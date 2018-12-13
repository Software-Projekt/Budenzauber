import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Veranstaltung } from 'app/shared/model/veranstaltung.model';
import { VeranstaltungService } from './veranstaltung.service';
import { VeranstaltungComponent } from './veranstaltung.component';
import { VeranstaltungDetailComponent } from './veranstaltung-detail.component';
import { VeranstaltungUpdateComponent } from './veranstaltung-update.component';
import { VeranstaltungDeletePopupComponent } from './veranstaltung-delete-dialog.component';
import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';

@Injectable({ providedIn: 'root' })
export class VeranstaltungResolve implements Resolve<IVeranstaltung> {
    constructor(private service: VeranstaltungService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Veranstaltung> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Veranstaltung>) => response.ok),
                map((veranstaltung: HttpResponse<Veranstaltung>) => veranstaltung.body)
            );
        }
        return of(new Veranstaltung());
    }
}

export const veranstaltungRoute: Routes = [
    {
        path: 'veranstaltung',
        component: VeranstaltungComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Veranstaltungs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'veranstaltung/:id/view',
        component: VeranstaltungDetailComponent,
        resolve: {
            veranstaltung: VeranstaltungResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Veranstaltungs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'veranstaltung/new',
        component: VeranstaltungUpdateComponent,
        resolve: {
            veranstaltung: VeranstaltungResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Veranstaltungs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'veranstaltung/:id/edit',
        component: VeranstaltungUpdateComponent,
        resolve: {
            veranstaltung: VeranstaltungResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Veranstaltungs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const veranstaltungPopupRoute: Routes = [
    {
        path: 'veranstaltung/:id/delete',
        component: VeranstaltungDeletePopupComponent,
        resolve: {
            veranstaltung: VeranstaltungResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Veranstaltungs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
