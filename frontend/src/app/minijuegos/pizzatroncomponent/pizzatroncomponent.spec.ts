import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Pizzatroncomponent } from './pizzatroncomponent';

describe('Pizzatroncomponent', () => {
  let component: Pizzatroncomponent;
  let fixture: ComponentFixture<Pizzatroncomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Pizzatroncomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Pizzatroncomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
