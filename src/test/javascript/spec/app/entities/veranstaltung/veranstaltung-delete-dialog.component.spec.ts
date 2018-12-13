/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BudenzauberTestModule } from '../../../test.module';
import { VeranstaltungDeleteDialogComponent } from 'app/entities/veranstaltung/veranstaltung-delete-dialog.component';
import { VeranstaltungService } from 'app/entities/veranstaltung/veranstaltung.service';

describe('Component Tests', () => {
    describe('Veranstaltung Management Delete Component', () => {
        let comp: VeranstaltungDeleteDialogComponent;
        let fixture: ComponentFixture<VeranstaltungDeleteDialogComponent>;
        let service: VeranstaltungService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [VeranstaltungDeleteDialogComponent]
            })
                .overrideTemplate(VeranstaltungDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VeranstaltungDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeranstaltungService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
