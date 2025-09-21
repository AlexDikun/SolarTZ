import { Component } from '@angular/core';
import { MainBsService } from './services/main-bs.service';
import { DatePipe, CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-main',
  imports: [
    DatePipe,
    CurrencyPipe
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
  providers: [
    MainBsService
  ]
})
export class MainComponent  {
  curDate :Date = new Date();
  price1 :number = 5000;
  price2 = 'бесплатно';
}
