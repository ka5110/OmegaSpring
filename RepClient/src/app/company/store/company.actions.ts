import { createAction, props } from '@ngrx/store';
import { ItemModel } from '../model/item.model';

export const loadItemsOfCompany = createAction(
  '[Company - Page] Begin Loading Items of company',
  props<{ company: string }>()
);

export const companySelected = createAction(
  '[Dashboard -> Company Pages] Incoming Action Company Selected',
  props<{ selectedCompany: string }>()
);

export const itemsOfCompanyLoaded = createAction(
  '[Company - Effects] Loaded of items finished successfully',
  props<{items: ItemModel[]}>()
);
