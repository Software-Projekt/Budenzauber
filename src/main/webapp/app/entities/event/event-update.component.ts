import { Component, Input, NgZone, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IUser, UserService } from 'app/core';
import { MatDialog } from '@angular/material';
import { Photo } from 'app/shared/model/photo.model';
import { PhotoService } from 'app/entities/photo';
import { FileUploader, FileDropDirective } from 'ng2-file-upload';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { formatDate } from '@angular/common';
import { tick } from '@angular/core/testing';

@Component({
    selector: 'jhi-event-update',
    templateUrl: './event-update.component.html',
    styleUrls: ['./dialog.css']
})
export class EventUpdateComponent implements OnInit {
    uploadForm: FormGroup;
    acceptForm: FormGroup;

    date: Date;

    data: any;

    selectedFiles: FileList;
    progress: { percentage: number } = { percentage: 0 };

    event: IEvent;
    isSaving: boolean;

    users: IUser[];
    archivierungsDatum: string;
    erstellungsDatum: string;

    photos: Set<File> = new Set();
    taken: string;
    uploaded: string;

    public uploader: FileUploader = new FileUploader({
        isHTML5: true
    });

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected eventService: EventService,
        protected activatedRoute: ActivatedRoute,
        public dialog: MatDialog,
        protected photoService: PhotoService,
        public fb: FormBuilder,
        protected userService: UserService,
        private zone: NgZone
    ) {}

    uploadSubmit() {
        this.progress.percentage = 0;
        for (let i = 0; i < this.uploader.queue.length; i++) {
            const fileItem = this.uploader.queue[i]._file;
            if (fileItem.size > 5000000) {
                alert('Eine Datei darf maximal 5MB groß sein');
                return;
            }
        }
        for (let j = 0; j < this.uploader.queue.length; j++) {
            const data = new FormData();
            const fileItem = this.uploader.queue[j]._file;

            data.append('file', fileItem);
            data.append('fileSeq', 'seq' + j);
            data.append('dataType', this.uploadForm.controls.type.value);

            this.photoService.upload(data, this.event.id).subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    this.progress.percentage = Math.round((100 * event.loaded) / event.total);
                } else if (event instanceof HttpResponse) {
                    console.log('File is completely uploaded!');
                }
            });
        }
        this.uploader.clearQueue();
    }

    ngOnInit() {
        this.date = new Date();

        this.uploadForm = this.fb.group({
            document: [null, null],
            type: [null, Validators.compose([Validators.required])]
        });

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ event }) => {
            this.event = event;
            this.archivierungsDatum = this.event.archivierungsDatum != null ? this.event.archivierungsDatum.format(DATE_TIME_FORMAT) : null;
            this.erstellungsDatum = this.event.erstellungsDatum != null ? this.event.erstellungsDatum.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    resetProgress() {
        this.progress.percentage = 0;
    }

    // getAllImages() {
    //     this.data = this.photoService.getFiles(this.event.id);
    //     console.log(this.data);
    //
    // }

    selectFile(event) {
        this.selectedFiles = event.target.files;
    }

    // upload() {
    //     this.progress.percentage = 0;
    //
    //     this.currentFileUpload = this.selectedFiles.item(0);
    //     this.photoService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
    //         if (event.type === HttpEventType.UploadProgress) {
    //             this.progress.percentage = Math.round(100 * event.loaded / event.total);
    //         } else if (event instanceof HttpResponse) {
    //             console.log('File is completely uploaded!');
    //         }
    //     });
    //     this.selectedFiles = undefined;
    // }

    // addFiles() {
    //     this.file.nativeElement.click();
    // }

    // onFilesAdded() {
    //     const files: { [key: string]: File } = this.file.nativeElement.files;
    //     for (let key in files) {
    //         if (!isNaN(parseInt(key))) {
    //             this.photos.add(files[key]);
    //         }
    //     }
    // }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.event.id !== undefined) {
            this.subscribeToSaveResponse(this.eventService.update(this.event));
        } else {
            this.subscribeToSaveResponse(this.eventService.create(this.event));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
        result.subscribe((res: HttpResponse<IEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    updateStatus(status: boolean) {
        this.event.freigegeben = status;
        this.save();
    }
}
