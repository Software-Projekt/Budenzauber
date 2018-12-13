import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';

@Component({
    selector: 'jhi-veranstaltung-detail',
    templateUrl: './veranstaltung-detail.component.html'
})
export class VeranstaltungDetailComponent implements OnInit {
    veranstaltung: IVeranstaltung;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ veranstaltung }) => {
            this.veranstaltung = veranstaltung;
        });
    }

    previousState() {
        window.history.back();
    }
}
