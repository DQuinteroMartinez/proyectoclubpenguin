import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Administracioncomponent } from './administracioncomponent';

describe('Administracioncomponent', () => {
  let component: Administracioncomponent;
  let fixture: ComponentFixture<Administracioncomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Administracioncomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Administracioncomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
