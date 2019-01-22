import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpEventType, HttpEvent } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPhoto } from 'app/shared/model/photo.model';

type EntityResponseType = HttpResponse<IPhoto>;
type EntityArrayResponseType = HttpResponse<IPhoto[]>;

@Injectable({ providedIn: 'root' })
export class PhotoService {
    public resourceUrl = SERVER_API_URL + 'api/photos';

    constructor(protected http: HttpClient) {}

    upload(data: FormData, id: number): Observable<HttpEvent<{}>> {
        const req = new HttpRequest('POST', this.resourceUrl + '/' + id, data, {
            reportProgress: true,
            responseType: 'text'
        });

        return this.http.request(req);
    }

    /*upload(photos: Set<File>): {[key: string]: Observable<number>} {
            // resulting Map
            const status = {};

            photos.forEach(file => {
                // multipart-form for every file
                const formData: FormData = new FormData();
                formData.append('file', file, file.name);

                // create a http-post request and pass the form
                // tell it to report the upload progress
                const req = new HttpRequest('POST', this.resourceUrl + '/upload',
                    formData, {
                    reportProgress: true,
                    responseType: 'text'
                    });

                // create a newprogress-subject for every file
                const progress = new Subject<number>();

                // send the http-request and subscribe for progress-updates
                this.http.request(req).subscribe(event => {
                    if (event.type === HttpEventType.UploadProgress) {
                        // calculate the upload percentage
                        const percentDone = Math.round(100 * event.loaded / event.total);

                        // pass the percentage into progress-stream
                        progress.next(percentDone);
                        console.log(percentDone);
                    } else if (event instanceof HttpResponse) {
                        // Close the progress-stream if we get an answer from API
                        // Upload complete
                        progress.complete();

                }
                });

            });
            // return Map of progress.observables
            return status;
        }*/

    getFiles(): Observable<any> {
        return this.http.get(this.resourceUrl + '/getallfiles');
    }

    create(photo: IPhoto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(photo);
        return this.http
            .post<IPhoto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(photo: IPhoto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(photo);
        return this.http
            .put<IPhoto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPhoto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPhoto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(photo: IPhoto): IPhoto {
        const copy: IPhoto = Object.assign({}, photo, {
            taken: photo.taken != null && photo.taken.isValid() ? photo.taken.toJSON() : null,
            uploaded: photo.uploaded != null && photo.uploaded.isValid() ? photo.uploaded.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.taken = res.body.taken != null ? moment(res.body.taken) : null;
            res.body.uploaded = res.body.uploaded != null ? moment(res.body.uploaded) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((photo: IPhoto) => {
                photo.taken = photo.taken != null ? moment(photo.taken) : null;
                photo.uploaded = photo.uploaded != null ? moment(photo.uploaded) : null;
            });
        }
        return res;
    }
}
