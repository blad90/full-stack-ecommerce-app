import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';
  private categoryUrl = 'http://localhost:8080/api/product-category';

  constructor(private httpClient: HttpClient) {}

  getProductList(categoryId: number): Observable<Product[]> {
    // Need to build URL based on category id
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${categoryId}`;
    return this.httpClient
      .get<GetResponseProducts>(searchUrl)
      .pipe(map((response) => response._embedded.product));
  }

  getProductCategories(): Observable<ProductCategory[]> {
    return this.httpClient
      .get<GetResponseProductCategories>(this.categoryUrl)
      .pipe(map((response) => response._embedded.productCategory));
  }
}

interface GetResponseProducts {
  _embedded: {
    product: Product[];
  };
}

interface GetResponseProductCategories {
  _embedded: {
    productCategory: ProductCategory[];
  };
}
