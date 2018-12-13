import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVeranstaltung } from 'app/shared/model/veranstaltung.model';

type EntityResponseType = HttpResponse<IVeranstaltung>;
type EntityArrayResponseType = HttpResponse<IVeranstaltung[]>;

@Injectable({ providedIn: 'root' })
export class VeranstaltungService {
    public resourceUrl = SERVER_API_URL + 'api/veranstaltungs';

    constructor(private http: HttpClient) {}

    create(veranstaltung: IVeranstaltung): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(veranstaltung);
        return this.http
            .post<IVeranstaltung>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(veranstaltung: IVeranstaltung): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(veranstaltung);
        return this.http
            .put<IVeranstaltung>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVeranstaltung>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVeranstaltung[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(veranstaltung: IVeranstaltung): IVeranstaltung {
        const copy: IVeranstaltung = Object.assign({}, veranstaltung, {
            archivierungsDatum:
                veranstaltung.archivierungsDatum != null && veranstaltung.archivierungsDatum.isValid()
                    ? veranstaltung.archivierungsDatum.toJSON()
                    : null,
            erstellungsDatum:
                veranstaltung.erstellungsDatum != null && veranstaltung.erstellungsDatum.isValid()
                    ? veranstaltung.erstellungsDatum.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.archivierungsDatum = res.body.archivierungsDatum != null ? moment(res.body.archivierungsDatum) : null;
            res.body.erstellungsDatum = res.body.erstellungsDatum != null ? moment(res.body.erstellungsDatum) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((veranstaltung: IVeranstaltung) => {
                veranstaltung.archivierungsDatum =
                    veranstaltung.archivierungsDatum != null ? moment(veranstaltung.archivierungsDatum) : null;
                veranstaltung.erstellungsDatum = veranstaltung.erstellungsDatum != null ? moment(veranstaltung.erstellungsDatum) : null;
            });
        }
        return res;
    }
}
