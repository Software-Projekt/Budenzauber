import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBild } from 'app/shared/model/bild.model';

type EntityResponseType = HttpResponse<IBild>;
type EntityArrayResponseType = HttpResponse<IBild[]>;

@Injectable({ providedIn: 'root' })
export class BildService {
    public resourceUrl = SERVER_API_URL + 'api/bilds';

    constructor(private http: HttpClient) {}

    create(bild: IBild): Observable<EntityResponseType> {
        return this.http.post<IBild>(this.resourceUrl, bild, { observe: 'response' });
    }

    update(bild: IBild): Observable<EntityResponseType> {
        return this.http.put<IBild>(this.resourceUrl, bild, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBild>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBild[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
