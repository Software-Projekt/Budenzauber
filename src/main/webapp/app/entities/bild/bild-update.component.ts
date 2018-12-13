import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBild } from 'app/shared/model/bild.model';
import { BildService } from './bild.service';
import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';
import { VeranstaltungService } from 'app/entities/veranstaltung';

@Component({
    selector: 'jhi-bild-update',
    templateUrl: './bild-update.component.html'
})
export class BildUpdateComponent implements OnInit {
    bild: IBild;
    isSaving: boolean;

    veranstaltungs: IVeranstaltung[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bildService: BildService,
        private veranstaltungService: VeranstaltungService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bild }) => {
            this.bild = bild;
        });
        this.veranstaltungService.query().subscribe(
            (res: HttpResponse<IVeranstaltung[]>) => {
                this.veranstaltungs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bild.id !== undefined) {
            this.subscribeToSaveResponse(this.bildService.update(this.bild));
        } else {
            this.subscribeToSaveResponse(this.bildService.create(this.bild));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBild>>) {
        result.subscribe((res: HttpResponse<IBild>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVeranstaltungById(index: number, item: IVeranstaltung) {
        return item.id;
    }
}
