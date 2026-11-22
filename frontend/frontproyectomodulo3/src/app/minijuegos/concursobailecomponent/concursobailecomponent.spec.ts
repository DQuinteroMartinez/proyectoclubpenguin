import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Concursobailecomponent } from './concursobailecomponent';

describe('Concursobailecomponent', () => {
  let component: Concursobailecomponent;
  let fixture: ComponentFixture<Concursobailecomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Concursobailecomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Concursobailecomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
