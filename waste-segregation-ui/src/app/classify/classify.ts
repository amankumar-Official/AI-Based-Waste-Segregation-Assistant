import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WasteService, ClassifyResponse } from '../waste';

@Component({
  selector: 'app-classify',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './classify.html',
  styleUrls: ['./classify.css']
})
export class Classify {
  item = '';
  imageBase64: string | null = null;
  result: ClassifyResponse | null = null;
  loading = false;
  error = '';

  exampleChips = ['banana peel', 'broken charger cable', 'polythene bag'];

  constructor(private wasteService: WasteService, private cdr: ChangeDetectorRef) { }

  selectChip(chip: string) {
    this.item = chip;
    this.onSubmit();
  }

  getCategoryIcon(category: string): string {
    if (!category) return '';
    const cat = category.toLowerCase();
    if (cat === 'recyclable') return '♻️';
    if (cat === 'organic') return '🍂';
    if (cat === 'e_waste') return '🔌';
    if (cat === 'hazardous') return '⚠️';
    if (cat === 'landfill') return '🗑️';
    return '📦';
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imageBase64 = e.target.result;
        this.cdr.detectChanges();
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    const trimmedItem = this.item.trim();
    if (!trimmedItem && !this.imageBase64) return;
    this.loading = true;
    this.error = '';
    this.result = null;

    this.wasteService.classify(trimmedItem, this.imageBase64 || undefined).subscribe({
      next: (res) => {
        this.result = res;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = 'Something went wrong. Please try again.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}
