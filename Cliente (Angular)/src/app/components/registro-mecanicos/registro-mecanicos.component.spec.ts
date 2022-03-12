import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroMecanicosComponent } from './registro-mecanicos.component';

describe('RegistroMecanicosComponent', () => {
  let component: RegistroMecanicosComponent;
  let fixture: ComponentFixture<RegistroMecanicosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroMecanicosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistroMecanicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
