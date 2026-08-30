import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-gestion-concours',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './gestion-concours.html',
  styleUrl: './gestion-concours.css',
})
export class GestionConcours implements OnInit {
  concoursListe: any[] = [];

  formulaireOuvert = false;
  titre = '';
  description = '';
  dateLimite = '';
  dateDeliberation = '';
  erreurDate = '';

  epreuvesParConcours: { [concoursId: number]: any[] } = {};
  concoursOuvertEpreuves: number | null = null;
  nomEpreuve = '';
  coefficientEpreuve: number | null = null;
  dureeEpreuve: number | null = null;

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.charger();
  }

  charger() {
    this.http.get<any[]>(`${environment.apiUrl}/concours`).subscribe({
      next: (data) => {
        this.concoursListe = data;
        this.cdr.detectChanges();
      },
    });
  }

  ouvrirFormulaire() {
    this.formulaireOuvert = true;
    this.titre = '';
    this.description = '';
    this.dateLimite = '';
    this.dateDeliberation = '';
    this.erreurDate = '';
  }

  fermerFormulaire() {
    this.formulaireOuvert = false;
  }

  onSubmit() {
    if (this.dateLimite >= this.dateDeliberation) {
      this.erreurDate = 'La date limite doit être antérieure à la date de délibération';
      return;
    }
    this.erreurDate = '';

    const nouveauConcours = {
      titre: this.titre,
      description: this.description,
      dateLimite: this.dateLimite,
      dateDeliberation: this.dateDeliberation,
    };

    this.http.post(`${environment.apiUrl}/concours`, nouveauConcours).subscribe({
      next: () => {
        this.fermerFormulaire();
        this.charger();
      },
    });
  }

  publierResultats(id: number) {
    this.http.put(`${environment.apiUrl}/concours/${id}/publier-resultats`, {}).subscribe({
      next: () => this.charger(),
    });
  }

  toggleEpreuves(concoursId: number) {
    if (this.concoursOuvertEpreuves === concoursId) {
      this.concoursOuvertEpreuves = null;
      return;
    }
    this.concoursOuvertEpreuves = concoursId;
    this.chargerEpreuves(concoursId);
  }

  chargerEpreuves(concoursId: number) {
    this.http.get<any[]>(`${environment.apiUrl}/concours/${concoursId}/epreuves`).subscribe({
      next: (data) => {
        this.epreuvesParConcours[concoursId] = data;
        this.cdr.detectChanges();
      },
    });
  }

  ajouterEpreuve(concoursId: number) {
    const nouvelleEpreuve = {
      nom: this.nomEpreuve,
      coefficient: this.coefficientEpreuve,
      duree: this.dureeEpreuve,
    };

    this.http
      .post(`${environment.apiUrl}/concours/${concoursId}/epreuves`, nouvelleEpreuve)
      .subscribe({
        next: () => {
          this.fermerFormulaireEpreuve();
          this.chargerEpreuves(concoursId);
        },
      });
  }

  concoursEpreuveOuverte: number | null = null;

  ouvrirFormulaireEpreuve(concoursId: number) {
    this.concoursEpreuveOuverte = concoursId;
    this.nomEpreuve = '';
    this.coefficientEpreuve = null;
    this.dureeEpreuve = null;
  }

  fermerFormulaireEpreuve() {
    this.concoursEpreuveOuverte = null;
  }
}
