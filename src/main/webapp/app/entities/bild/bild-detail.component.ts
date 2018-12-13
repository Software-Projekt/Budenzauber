import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBild } from 'app/shared/model/bild.model';

@Component({
    selector: 'jhi-bild-detail',
    templateUrl: './bild-detail.component.html'
})
export class BildDetailComponent implements OnInit {
    bild: IBild;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bild }) => {
            this.bild = bild;
        });
    }

    previousState() {
        window.history.back();
    }
}
