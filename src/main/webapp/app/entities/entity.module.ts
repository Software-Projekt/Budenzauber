import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BudenzauberEventModule } from './event/event.module';
import { BudenzauberPhotoModule } from './photo/photo.module';
import { BudenzauberTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BudenzauberEventModule,
        BudenzauberPhotoModule,
        BudenzauberTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BudenzauberEntityModule {}
