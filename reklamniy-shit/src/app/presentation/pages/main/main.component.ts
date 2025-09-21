import { Component } from '@angular/core';
import { MainBsService } from './services/main-bs.service';

@Component({
  selector: 'app-main',
  imports: [],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
  providers: [
    MainBsService
  ]
})
export class MainComponent {

}
