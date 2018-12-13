/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BudenzauberTestModule } from '../../../test.module';
import { VeranstaltungUpdateComponent } from 'app/entities/veranstaltung/veranstaltung-update.component';
import { VeranstaltungService } from 'app/entities/veranstaltung/veranstaltung.service';
import { Veranstaltung } from 'app/shared/model/veranstaltung.model';

describe('Component Tests', () => {
    describe('Veranstaltung Management Update Component', () => {
        let comp: VeranstaltungUpdateComponent;
        let fixture: ComponentFixture<VeranstaltungUpdateComponent>;
        let service: VeranstaltungService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [VeranstaltungUpdateComponent]
            })
                .overrideTemplate(VeranstaltungUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VeranstaltungUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeranstaltungService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Veranstaltung(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.veranstaltung = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Veranstaltung();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.veranstaltung = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
