import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";

import {SERVER_API_URL} from "../../common/constants";
import {Venue} from "./venue.model";

@Injectable({
    providedIn: 'root'
})
export class VenueService {

    public resourceUrl = SERVER_API_URL + '/api/venues';

    constructor(
        protected http: HttpClient
    ) {
    }

    create(venue: Venue): Observable<HttpResponse<Venue>> {
        return this.http.post<Venue>(this.resourceUrl, venue, {observe: 'response'});
    }

    query(): Observable<HttpResponse<Venue[]>> {
        return this.http.get<Venue[]>(this.resourceUrl, {observe: 'response'});
    }

}
