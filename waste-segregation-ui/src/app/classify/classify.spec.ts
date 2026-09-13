import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Classify } from './classify';

describe('Classify', () => {
  let component: Classify;
  let fixture: ComponentFixture<Classify>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Classify],
    }).compileComponents();

    fixture = TestBed.createComponent(Classify);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
