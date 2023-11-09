import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductWomenComponent } from './product-women.component';

describe('ProductWomemComponent', () => {
  let component: ProductWomenComponent;
  let fixture: ComponentFixture<ProductWomenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductWomenComponent]
    });
    fixture = TestBed.createComponent(ProductWomenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
