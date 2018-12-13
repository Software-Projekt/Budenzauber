import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';
import { VeranstaltungService } from './veranstaltung.service';

@Component({
    selector: 'jhi-veranstaltung-delete-dialog',
    templateUrl: './veranstaltung-delete-dialog.component.html'
})
export class VeranstaltungDeleteDialogComponent {
    veranstaltung: IVeranstaltung;

    constructor(
        private veranstaltungService: VeranstaltungService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.veranstaltungService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'veranstaltungListModification',
                content: 'Deleted an veranstaltung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-veranstaltung-delete-popup',
    template: ''
})
export class VeranstaltungDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ veranstaltung }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VeranstaltungDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.veranstaltung = veranstaltung;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
