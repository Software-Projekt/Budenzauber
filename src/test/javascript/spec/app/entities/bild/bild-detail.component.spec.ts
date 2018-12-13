/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BudenzauberTestModule } from '../../../test.module';
import { BildDetailComponent } from 'app/entities/bild/bild-detail.component';
import { Bild } from 'app/shared/model/bild.model';

describe('Component Tests', () => {
    describe('Bild Management Detail Component', () => {
        let comp: BildDetailComponent;
        let fixture: ComponentFixture<BildDetailComponent>;
        const route = ({ data: of({ bild: new Bild(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BudenzauberTestModule],
                declarations: [BildDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BildDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BildDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bild).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
