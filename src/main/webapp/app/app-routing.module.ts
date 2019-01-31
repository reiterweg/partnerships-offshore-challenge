import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {VenueComponent} from "./entities/venue/venue.component";
import {EventComponent} from "./entities/event/event.component";

const routes: Routes = [
    {path: 'events', component: EventComponent},
    {path: 'venues', component: VenueComponent},
    {path: '**', pathMatch: 'full', redirectTo: 'events'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
