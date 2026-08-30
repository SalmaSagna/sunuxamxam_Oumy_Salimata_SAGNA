import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { ConcoursList } from './pages/candidat/concours-list/concours-list';
import { DepotCandidature } from './pages/candidat/depot-candidature/depot-candidature';
import { MesCandidatures } from './pages/candidat/mes-candidatures/mes-candidatures';
import { GestionConcours } from './pages/admin/gestion-concours/gestion-concours';
import { GestionCandidatures } from './pages/admin/gestion-candidatures/gestion-candidatures';
import { Navbar } from './navbar/navbar';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: Navbar,
    children: [
      { path: '', redirectTo: 'concours', pathMatch: 'full' },
      { path: 'concours', component: ConcoursList },
      { path: 'concours/:id/postuler', component: DepotCandidature },
      { path: 'mes-candidatures', component: MesCandidatures },
      { path: 'admin/concours', component: GestionConcours },
      { path: 'admin/candidatures', component: GestionCandidatures },
    ],
  },
];
