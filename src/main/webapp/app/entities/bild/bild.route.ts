import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bild } from 'app/shared/model/bild.model';
import { BildService } from './bild.service';
import { BildComponent } from './bild.component';
import { BildDetailComponent } from './bild-detail.component';
import { BildUpdateComponent } from './bild-update.component';
import { BildDeletePopupComponent } from './bild-delete-dialog.component';
import { IBild } from 'app/shared/model/bild.model';

@Injectable({ providedIn: 'root' })
export class BildResolve implements Resolve<IBild> {
    constructor(private service: BildService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Bild> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bild>) => response.ok),
                map((bild: HttpResponse<Bild>) => bild.body)
            );
        }
        return of(new Bild());
    }
}

export const bildRoute: Routes = [
    {
        path: 'bild',
        component: BildComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bild/:id/view',
        component: BildDetailComponent,
        resolve: {
            bild: BildResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bild/new',
        component: BildUpdateComponent,
        resolve: {
            bild: BildResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bild/:id/edit',
        component: BildUpdateComponent,
        resolve: {
            bild: BildResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bildPopupRoute: Routes = [
    {
        path: 'bild/:id/delete',
        component: BildDeletePopupComponent,
        resolve: {
            bild: BildResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bilds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
