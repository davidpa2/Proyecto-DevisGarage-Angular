import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReparacionesMecanicoComponent } from './reparaciones-mecanico.component';

describe('ReparacionesMecanicoComponent', () => {
  let component: ReparacionesMecanicoComponent;
  let fixture: ComponentFixture<ReparacionesMecanicoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReparacionesMecanicoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReparacionesMecanicoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
