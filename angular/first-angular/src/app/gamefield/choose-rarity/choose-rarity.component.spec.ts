import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseRarityComponent } from './choose-rarity.component';

describe('ChooseFractionComponent', () => {
  let component: ChooseRarityComponent;
  let fixture: ComponentFixture<ChooseRarityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChooseRarityComponent]
    });
    fixture = TestBed.createComponent(ChooseRarityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
