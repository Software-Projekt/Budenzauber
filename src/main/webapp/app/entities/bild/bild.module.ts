import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BudenzauberSharedModule } from 'app/shared';
import {
    BildComponent,
    BildDetailComponent,
    BildUpdateComponent,
    BildDeletePopupComponent,
    BildDeleteDialogComponent,
    bildRoute,
    bildPopupRoute
} from './';

const ENTITY_STATES = [...bildRoute, ...bildPopupRoute];

@NgModule({
    imports: [BudenzauberSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BildComponent, BildDetailComponent, BildUpdateComponent, BildDeleteDialogComponent, BildDeletePopupComponent],
    entryComponents: [BildComponent, BildUpdateComponent, BildDeleteDialogComponent, BildDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BudenzauberBildModule {}
