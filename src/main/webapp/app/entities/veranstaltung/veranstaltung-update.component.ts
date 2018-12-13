import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';
import { VeranstaltungService } from './veranstaltung.service';

@Component({
    selector: 'jhi-veranstaltung-update',
    templateUrl: './veranstaltung-update.component.html'
})
export class VeranstaltungUpdateComponent implements OnInit {
    veranstaltung: IVeranstaltung;
    isSaving: boolean;
    archivierungsDatum: string;
    erstellungsDatum: string;

    constructor(private veranstaltungService: VeranstaltungService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ veranstaltung }) => {
            this.veranstaltung = veranstaltung;
            this.archivierungsDatum =
                this.veranstaltung.archivierungsDatum != null ? this.veranstaltung.archivierungsDatum.format(DATE_TIME_FORMAT) : null;
            this.erstellungsDatum =
                this.veranstaltung.erstellungsDatum != null ? this.veranstaltung.erstellungsDatum.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.veranstaltung.archivierungsDatum = this.archivierungsDatum != null ? moment(this.archivierungsDatum, DATE_TIME_FORMAT) : null;
        this.veranstaltung.erstellungsDatum = this.erstellungsDatum != null ? moment(this.erstellungsDatum, DATE_TIME_FORMAT) : null;
        if (this.veranstaltung.id !== undefined) {
            this.subscribeToSaveResponse(this.veranstaltungService.update(this.veranstaltung));
        } else {
            this.subscribeToSaveResponse(this.veranstaltungService.create(this.veranstaltung));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVeranstaltung>>) {
        result.subscribe((res: HttpResponse<IVeranstaltung>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
