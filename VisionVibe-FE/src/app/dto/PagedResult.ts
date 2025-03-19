import { Pageable, Sort } from "./Pageable";

export interface PagedResult<T> {
  content: T;
  pageable: Pageable;
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: Sort;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}