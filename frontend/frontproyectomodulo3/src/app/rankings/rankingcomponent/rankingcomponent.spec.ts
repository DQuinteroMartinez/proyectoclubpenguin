import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Rankingcomponent } from './rankingcomponent';

describe('Rankingcomponent', () => {
  let component: Rankingcomponent;
  let fixture: ComponentFixture<Rankingcomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Rankingcomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Rankingcomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
