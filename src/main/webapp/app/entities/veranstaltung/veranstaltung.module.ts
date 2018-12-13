import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BudenzauberSharedModule } from 'app/shared';
import {
    VeranstaltungComponent,
    VeranstaltungDetailComponent,
    VeranstaltungUpdateComponent,
    VeranstaltungDeletePopupComponent,
    VeranstaltungDeleteDialogComponent,
    veranstaltungRoute,
    veranstaltungPopupRoute
} from './';

const ENTITY_STATES = [...veranstaltungRoute, ...veranstaltungPopupRoute];

@NgModule({
    imports: [BudenzauberSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VeranstaltungComponent,
        VeranstaltungDetailComponent,
        VeranstaltungUpdateComponent,
        VeranstaltungDeleteDialogComponent,
        VeranstaltungDeletePopupComponent
    ],
    entryComponents: [
        VeranstaltungComponent,
        VeranstaltungUpdateComponent,
        VeranstaltungDeleteDialogComponent,
        VeranstaltungDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BudenzauberVeranstaltungModule {}
