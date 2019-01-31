import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import * as moment from 'moment';

import {SERVER_API_URL} from "../../common/constants";
import {Event} from "./event.model";

@Injectable({
    providedIn: 'root'
})
export class EventService {

    public resourceUrl = SERVER_API_URL + '/api/events';

    constructor(
        protected http: HttpClient
    ) {
    }

    create(event: Event): Observable<HttpResponse<Event>> {
        const copy = this.convertDateFromClient(event);
        return this.http
            .post<Event>(this.resourceUrl, copy, {observe: 'response'})
            .pipe(map((response: HttpResponse<Event>) => this.convertDateFromServer(response)));
    }

    query(): Observable<HttpResponse<Event[]>> {
        return this.http
            .get<Event[]>(this.resourceUrl, {observe: 'response'})
            .pipe(map((response: HttpResponse<Event[]>) => this.convertDateArrayFromServer(response)));
    }

    protected convertDateFromClient(event: Event): Event {
        const copy: Event = Object.assign({}, event, {
            date: event.date != null && moment(event.date).isValid() ? event.date.toJSON() : null
        });

        return copy;
    }

    protected convertDateFromServer(response: HttpResponse<Event>): HttpResponse<Event> {
        if (response.body) {
            response.body.date = response.body.date != null ? moment(response.body.date) : null;
        }

        return response;
    }

    protected convertDateArrayFromServer(response: HttpResponse<Event[]>): HttpResponse<Event[]> {
        if (response.body) {
            response.body.forEach((event: Event) => {
                event.date = event.date != null ? moment(event.date) : null;
            });
        }

        return response;
    }

}
