import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError} from 'rxjs/operators';
import {GameNight} from '../model/gameNight';
import {General} from "../model/general";
import {Play} from "../model/play";

@Injectable({
  providedIn: 'root'
})
export class RestApiService {
// Define API
  apiURL = 'http://192.168.1.103:8080/api';

  constructor(private http: HttpClient) {
  }

  /*========================================
  CRUD Methods for consuming RESTful API
  =========================================*/
  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  // HttpClient API get() method => Fetch GeneralStats
  getGeneralStats(): Observable<General> {
    return this.http.get<General>(this.apiURL + '/stats/general')
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API get() method => Fetch Play list
  getPlays(): Observable<Play> {
    return this.http.get<Play>(this.apiURL + '/stats/plays')
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API get() method => Fetch GameNight list
  getGameNights(): Observable<GameNight> {
    return this.http.get<GameNight>(this.apiURL + '/gamenight')
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // HttpClient API get() method => Fetch GameNight
  getGameNght(id): Observable<GameNight> {
    return this.http.get<GameNight>(this.apiURL + '/gamenight/' + id)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  // Error handling
  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

}
