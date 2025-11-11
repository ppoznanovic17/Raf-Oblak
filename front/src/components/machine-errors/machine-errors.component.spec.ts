import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachineErrorsComponent } from './machine-errors.component';

describe('MachineErrorsComponent', () => {
  let component: MachineErrorsComponent;
  let fixture: ComponentFixture<MachineErrorsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MachineErrorsComponent]
    });
    fixture = TestBed.createComponent(MachineErrorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
