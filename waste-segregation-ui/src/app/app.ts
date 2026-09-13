import { Component } from '@angular/core';
import { Classify } from './classify/classify';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Classify],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  title = 'waste-segregation-ui';
}
