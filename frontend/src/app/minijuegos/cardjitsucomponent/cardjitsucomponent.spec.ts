import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Cardjitsucomponent } from './cardjitsucomponent';

describe('Cardjitsucomponent', () => {
  let component: Cardjitsucomponent;
  let fixture: ComponentFixture<Cardjitsucomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Cardjitsucomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Cardjitsucomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
