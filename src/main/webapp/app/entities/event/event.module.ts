import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BudenzauberSharedModule } from 'app/shared';
import { BudenzauberAdminModule } from 'app/admin/admin.module';
import {
    EventComponent,
    EventDetailComponent,
    EventUpdateComponent,
    EventDeletePopupComponent,
    EventDeleteDialogComponent,
    eventRoute,
    eventPopupRoute
} from './';
import { PhotoService } from 'app/entities/photo';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatDialogModule, MatListModule, MatProgressBarModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';

const ENTITY_STATES = [...eventRoute, ...eventPopupRoute];

@NgModule({
    imports: [
        BudenzauberSharedModule,
        BudenzauberAdminModule,
        RouterModule.forChild(ENTITY_STATES),
        CommonModule,
        MatButtonModule,
        MatDialogModule,
        MatListModule,
        FlexLayoutModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatProgressBarModule,
        ReactiveFormsModule,
        FormsModule,
        FileUploadModule
    ],
    declarations: [EventComponent, EventDetailComponent, EventUpdateComponent, EventDeleteDialogComponent, EventDeletePopupComponent],
    entryComponents: [EventComponent, EventDeleteDialogComponent, EventDeletePopupComponent, EventUpdateComponent],
    exports: [EventUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [PhotoService]
})
export class BudenzauberEventModule {}
