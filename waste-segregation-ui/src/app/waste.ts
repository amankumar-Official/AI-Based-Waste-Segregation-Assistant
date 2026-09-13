import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ClassifyResponse {
  category: string;
  disposalInstructions: string;
  reasoning: string;
  sustainabilityTip: string;
}

@Injectable({ providedIn: 'root' })
export class WasteService {
  private baseUrl = 'http://localhost:8080/api/waste';

  constructor(private http: HttpClient) {}

  classify(item: string, imageBase64?: string): Observable<ClassifyResponse> {
    return this.http.post<ClassifyResponse>(`${this.baseUrl}/classify`, { item, imageBase64 });
  }
}
