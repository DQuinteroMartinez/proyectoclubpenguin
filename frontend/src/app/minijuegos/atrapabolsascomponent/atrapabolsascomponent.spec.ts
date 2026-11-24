import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Atrapabolsascomponent } from './atrapabolsascomponent';

describe('Atrapabolsascomponent', () => {
  let component: Atrapabolsascomponent;
  let fixture: ComponentFixture<Atrapabolsascomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Atrapabolsascomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Atrapabolsascomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
