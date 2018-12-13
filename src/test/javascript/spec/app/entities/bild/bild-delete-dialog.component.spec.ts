/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BudenzauberTestModule } from '../../../test.module';
import { BildDeleteDialogComponent } from 'app/entities/bild/bild-delete-dialog.component';
import { BildService } from 'app/entities/bild/bild.service';

describe('Component Tests', () => {
    describe('Bild Management Delete Component', () => {
        let comp: BildDeleteDialogComponent;
        let fixture: ComponentFixture<BildDeleteDialogComponent>;
        let service: BildService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [BildDeleteDialogComponent]
            })
                .overrideTemplate(BildDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BildDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildService);
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
