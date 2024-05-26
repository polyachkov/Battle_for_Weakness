import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseFractionComponent } from './choose-fraction.component';

describe('ChooseFractionComponent', () => {
  let component: ChooseFractionComponent;
  let fixture: ComponentFixture<ChooseFractionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChooseFractionComponent]
    });
    fixture = TestBed.createComponent(ChooseFractionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
