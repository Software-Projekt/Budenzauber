/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BudenzauberTestModule } from '../../../test.module';
import { VeranstaltungDetailComponent } from 'app/entities/veranstaltung/veranstaltung-detail.component';
import { Veranstaltung } from 'app/shared/model/veranstaltung.model';

describe('Component Tests', () => {
    describe('Veranstaltung Management Detail Component', () => {
        let comp: VeranstaltungDetailComponent;
        let fixture: ComponentFixture<VeranstaltungDetailComponent>;
        const route = ({ data: of({ veranstaltung: new Veranstaltung(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [VeranstaltungDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VeranstaltungDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VeranstaltungDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.veranstaltung).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
