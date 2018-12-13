import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BudenzauberVeranstaltungModule } from './veranstaltung/veranstaltung.module';
import { BudenzauberBildModule } from './bild/bild.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BudenzauberVeranstaltungModule,
        BudenzauberBildModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BudenzauberEntityModule {}
