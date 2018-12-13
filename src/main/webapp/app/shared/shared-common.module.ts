import { NgModule } from '@angular/core';

import { BudenzauberSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [BudenzauberSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [BudenzauberSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BudenzauberSharedCommonModule {}
