/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BudenzauberTestModule } from '../../../test.module';
import { BildUpdateComponent } from 'app/entities/bild/bild-update.component';
import { BildService } from 'app/entities/bild/bild.service';
import { Bild } from 'app/shared/model/bild.model';

describe('Component Tests', () => {
    describe('Bild Management Update Component', () => {
        let comp: BildUpdateComponent;
        let fixture: ComponentFixture<BildUpdateComponent>;
        let service: BildService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [BildUpdateComponent]
            })
                .overrideTemplate(BildUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BildUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BildService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bild(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bild = entity;
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
                    const entity = new Bild();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bild = entity;
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
