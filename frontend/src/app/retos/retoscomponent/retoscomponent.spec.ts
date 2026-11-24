import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Retoscomponent } from './retoscomponent';

describe('Retoscomponent', () => {
  let component: Retoscomponent;
  let fixture: ComponentFixture<Retoscomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Retoscomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Retoscomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
