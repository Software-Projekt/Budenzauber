import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBild } from 'app/shared/model/bild.model';
import { BildService } from './bild.service';

@Component({
    selector: 'jhi-bild-delete-dialog',
    templateUrl: './bild-delete-dialog.component.html'
})
export class BildDeleteDialogComponent {
    bild: IBild;

    constructor(private bildService: BildService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bildService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bildListModification',
                content: 'Deleted an bild'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bild-delete-popup',
    template: ''
})
export class BildDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bild }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BildDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bild = bild;
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
