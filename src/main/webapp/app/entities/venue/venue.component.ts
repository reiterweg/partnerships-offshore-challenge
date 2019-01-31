import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AlertService} from "ngx-alerts";

import {Venue} from "./venue.model";
import {VenueService} from "./venue.service";

@Component({
    selector: 'app-venue',
    templateUrl: './venue.component.html'
})
export class VenueComponent implements OnInit {

    venues: Venue[];

    constructor(
        protected venueService: VenueService,
        private alertService: AlertService
    ) {
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.venueService.query().subscribe(
            (response: HttpResponse<Venue[]>) => {
                this.venues = response.body;
            },
            (response: HttpErrorResponse) => this.alertService.danger(response.message)
        );
    }

}
