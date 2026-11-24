import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Carritosurfercomponent } from './carritosurfercomponent';

describe('Carritosurfercomponent', () => {
  let component: Carritosurfercomponent;
  let fixture: ComponentFixture<Carritosurfercomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Carritosurfercomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Carritosurfercomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
