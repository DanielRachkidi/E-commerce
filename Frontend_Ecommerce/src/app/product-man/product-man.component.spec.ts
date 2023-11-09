import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductManComponent } from './product-man.component';

describe('ProductManComponent', () => {
  let component: ProductManComponent;
  let fixture: ComponentFixture<ProductManComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductManComponent]
    });
    fixture = TestBed.createComponent(ProductManComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
