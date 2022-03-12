import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroCocheComponent } from './registro-coche.component';

describe('RegistroCocheComponent', () => {
  let component: RegistroCocheComponent;
  let fixture: ComponentFixture<RegistroCocheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroCocheComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistroCocheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
