import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag';

@Component({
    selector: 'jhi-photo-update',
    templateUrl: './photo-update.component.html'
})
export class PhotoUpdateComponent implements OnInit {
    photo: IPhoto;
    isSaving: boolean;

    events: IEvent[];

    tags: ITag[];
    taken: string;
    uploaded: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected photoService: PhotoService,
        protected eventService: EventService,
        protected tagService: TagService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ photo }) => {
            this.photo = photo;
            this.taken = this.photo.taken != null ? this.photo.taken.format(DATE_TIME_FORMAT) : null;
            this.uploaded = this.photo.uploaded != null ? this.photo.uploaded.format(DATE_TIME_FORMAT) : null;
        });
        this.eventService.query().subscribe(
            (res: HttpResponse<IEvent[]>) => {
                this.events = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tagService.query().subscribe(
            (res: HttpResponse<ITag[]>) => {
                this.tags = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.photo, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.photo.taken = this.taken != null ? moment(this.taken, DATE_TIME_FORMAT) : null;
        this.photo.uploaded = this.uploaded != null ? moment(this.uploaded, DATE_TIME_FORMAT) : null;
        if (this.photo.id !== undefined) {
            this.subscribeToSaveResponse(this.photoService.update(this.photo));
        } else {
            this.subscribeToSaveResponse(this.photoService.create(this.photo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>) {
        result.subscribe((res: HttpResponse<IPhoto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEventById(index: number, item: IEvent) {
        return item.id;
    }

    trackTagById(index: number, item: ITag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
